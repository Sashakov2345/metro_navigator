package metro;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import metro.commands.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Metro {
    private boolean isCorrectFile = false;
    private Map<String, MetroLine> metroMap = new HashMap<>();
    private Map<String, LinkedList<Station>> fromFileMap = new HashMap<>();

    public Metro(String filePath) throws FileNotFoundException {
        try {
            readMetroFromFile(filePath);
            isCorrectFile = true;
        } catch (JsonSyntaxException e) {
            System.out.println("Incorrect file");
        }

    }

    private void printOutput(MetroLine metroLine) {
//        LinkedList<Station> sll = new LinkedList<>(metroLine.getStations());
//        Station depot = new Station("depot");
//        sll.addLast(depot);
//        sll.addFirst(depot);
//        sll.stream().forEach(System.out::println);
    }

    private void putNextAndPrevInStations() {
        metroMap.values().forEach(metroLine -> {
            metroLine.getStations().forEach(st->{

                st.getNext().forEach(str->{
                    st.getNextStations().add(metroLine.getStationMap().get(str));
                });
                st.getPrev().forEach(str->{
                    st.getPrevStations().add(metroLine.getStationMap().get(str));
                });
            });
        });
    }

    private void putTransferStations(){
        metroMap.values().forEach(metroLine -> {
            metroLine.getStations().forEach(st -> {

                ArrayList<Map<String, String>> transfer = st.getTransfer();
                if (!transfer.isEmpty()) {
                    for (int i = 0; i < transfer.size(); i++) {
                        String line = transfer.get(i).get("line");
                        String stationName = transfer.get(i).get("station");
                        st.getTransferList().add(metroMap.get(line).getStationMap().get(stationName));
                    }
                }
            });
        });
    }

    public void createMetroLineMaps(){
        metroMap.values().forEach(metroLine -> {
            Map<String,Station> stationMap=metroLine.getStationMap();
            for (Station st:metroLine.getStations()) {
                stationMap.put(st.getName(),st);
                st.setTransferList(new ArrayList<>());
                st.setNextStations(new ArrayList<>());
                st.setPrevStations(new ArrayList<>());
                st.setLine(metroLine.getLineName());
            }
        });
    }


    public void readMetroFromFile(String filePath) throws FileNotFoundException, JsonSyntaxException {
        Reader reader = new FileReader(filePath);
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        Type typeOfT = new TypeToken<Map<String, LinkedList<Station>>>() {
        }.getType();
        fromFileMap = gson.fromJson(reader, typeOfT);
        fromFileMap.keySet().stream().forEach(k -> {
            LinkedList<Station> stations = fromFileMap.get(k);
            MetroLine metroLine = new MetroLine(k, stations);
            metroMap.put(k, metroLine);
        });
        createMetroLineMaps();
        putNextAndPrevInStations();
        putTransferStations();
    }

    public void applyCommands() {
        Controller controller = new Controller();
        Scanner scanner = new Scanner(System.in);
        boolean isGoing = true;
        while (isGoing) {
            String command = scanner.nextLine();
            String regEx = "([^\"^\\s]+)\\s*|\"([^\"]+)\"\\s*";
            ArrayList<String> commandList = new ArrayList<>();
            Pattern pattern = Pattern.compile(regEx);
            Matcher matcher = pattern.matcher(command);
            String quotRegEx = "\".+\"";
            while (matcher.find()) {
                String s = matcher.group().trim();
                if (s.matches(quotRegEx)) {
                    s = s.substring(1, s.length() - 1);
                }
                commandList.add(s);
//               System.out.println(s);
            }
            try {
                String lineName;
                String stationName;
                switch (commandList.get(0)) {
                    case "/fastest-route":
                        controller.setCommand
                                (new ShortestRouteCommand(this, commandList.get(1), commandList.get(2)
                                        , commandList.get(3), commandList.get(4)));
                        break;
                    case "/route":
                        controller.setCommand
                                (new RouteCommand(this, commandList.get(1), commandList.get(2)
                                        , commandList.get(3), commandList.get(4)));
                        break;
                    case "/connect":
                        controller.setCommand
                                (new ConnectStationsCommand(this, commandList.get(1), commandList.get(2)
                                        , commandList.get(3), commandList.get(4)));
                        break;
//                    case "/output":
//                        lineName = commandList.get(1);
//                        controller.setCommand(new OutputCommand(this, lineName));
//                        break;
//                    case "/add-head":
//                        lineName = commandList.get(1);
//                        stationName = commandList.get(2);
//                        controller.setCommand(new AddHeadCommand(this, lineName, stationName));
//                        break;
//                    case "/remove":
//                        lineName = commandList.get(1);
//                        stationName = commandList.get(2);
//                        controller.setCommand(new RemoveCommand(this, lineName, stationName));
//                        break;
//                    case "/append":
//                        lineName = commandList.get(1);
//                        stationName = commandList.get(2);
//                        controller.setCommand(new AppendCommand(this, lineName, stationName));
//                        break;
                    case "/exit":
                        isGoing = false;
                        controller.setCommand(new ExitCommand(this));
                        break;
                    default:
                        controller.setCommand(new ErrorCommand(this));
                        break;
                }
            } catch (IndexOutOfBoundsException e) {
                controller.setCommand(new ErrorCommand(this));
            }
            controller.executeCommand();
        }
    }

    public void outputLine(String lineName) {
//        Optional<MetroLine> metroLine = Optional.ofNullable(metroMap.get(lineName));
//        if (metroLine.isPresent()) {
//            printOutput(metroLine.get());
//        } else {
//            Controller controller = new Controller();
//            controller.setCommand(new ErrorCommand(this));
//            controller.executeCommand();
//        }
    }


    public boolean isCorrectFile() {
        return isCorrectFile;
    }

    public void addHeadStation(String lineName, String newStation) {
//        Optional<MetroLine> metroLine = Optional.ofNullable(metroMap.get(lineName));
//        if (metroLine.isPresent()) {
//            metroLine.get().getStations().addFirst(new Station(newStation));
//        } else {
//            Controller controller = new Controller();
//            controller.setCommand(new ErrorCommand(this));
//            controller.executeCommand();
//        }
    }

    public void appendStation(String lineName, String newStation) {
//        Optional<MetroLine> metroLine = Optional.ofNullable(metroMap.get(lineName));
//        if (metroLine.isPresent()) {
//            metroLine.get().getStations().addLast(new Station(newStation));
//        } else {
//            Controller controller = new Controller();
//            controller.setCommand(new ErrorCommand(this));
//            controller.executeCommand();
//        }
//    }

//    private void removeIntersections(String stationName) {
//        metroMap.values()
//                .forEach(line -> line.getStations()
//                        .forEach(st -> st
//                                .getTransfer().removeIf(tr -> tr.get("station").equals(stationName))));
//    }
//
//    public void removeStation(String lineName, String stationName) {
//        Controller controller = new Controller();
//        controller.setCommand(new ErrorCommand(this));
//        Optional<MetroLine> metroLine = Optional.ofNullable(metroMap.get(lineName));
//        if (metroLine.isPresent()) {
//            List<Station> station = metroLine.get().getStations().stream()
//                    .filter(st -> st.getName().equals(stationName)).collect(Collectors.toList());
//            if (!station.isEmpty()) {
//                metroLine.get().getStations().remove(station.get(0));
//                removeIntersections(stationName);
//                return;
//            }
//            controller.executeCommand();
//        }
    }

    public void connectStations(String lineName1, String stationName1, String lineName2, String stationName2) {
//        Optional<MetroLine> metroLine1 = Optional.ofNullable(metroMap.get(lineName1));
//        Optional<MetroLine> metroLine2 = Optional.ofNullable(metroMap.get(lineName2));
//        if (metroLine1.isPresent() && metroLine2.isPresent()) {
//            List<Station> station1 = metroLine1.get().getStations().stream()
//                    .filter(st -> st.getName().equals(stationName1)).collect(Collectors.toList());
//            List<Station> station2 = metroLine2.get().getStations().stream()
//                    .filter(st -> st.getName().equals(stationName2)).collect(Collectors.toList());
//            if (!station1.isEmpty() && !station2.isEmpty()) {
//                Station st1 = station1.get(0);
//                Station st2 = station2.get(0);
//                Map<String, String> hashMap1 = new HashMap<>();
//                Map<String, String> hashMap2 = new HashMap<>();
//                hashMap1.put("line", lineName2);
//                hashMap1.put("station", stationName2);
//                hashMap2.put("line", lineName1);
//                hashMap2.put("station", stationName1);
//                st1.getTransfer().add(hashMap1);
//                st2.getTransfer().add(hashMap2);
//                return;
//            }
//        }
//        Controller controller = new Controller();
//        controller.setCommand(new ErrorCommand(this));
//        controller.executeCommand();
    }

    private Queue<QueueAdder> stationQueue = new ArrayDeque<>();
    private Station finishStation;
    private Station startStation;
    private Set<Station> checkedStationsSet = new HashSet<>();

    public void outputPath(String lineName1, String stationName1, String lineName2, String stationName2) {
        checkedStationsSet.clear();
        Optional<MetroLine> metroLine1 = Optional.ofNullable(metroMap.get(lineName1));
        Optional<MetroLine> metroLine2 = Optional.ofNullable(metroMap.get(lineName2));
        if (metroLine1.isPresent() && metroLine1.isPresent()) {
           if (metroLine1.get().getStationMap().containsKey(stationName1) &&
                   metroLine2.get().getStationMap().containsKey(stationName2)) {
                startStation = metroLine1.get().getStationMap().get(stationName1);
                finishStation = metroLine2.get().getStationMap().get(stationName2);
                stationQueue.add(new QueueAdder(null, startStation));
                checkedStationsSet.add(startStation);
                LinkedList<Station> pathList = findPath();
                if (!pathList.isEmpty()) {
                    pathList.stream().map(st -> st.getName()).forEach(System.out::println);
                    return;
                }
//                else {
//                    System.out.println("Path not found");
//                }

            }
        }
        Controller controller = new Controller();
        controller.setCommand(new ErrorCommand(this));
        controller.executeCommand();
    }

    private static class QueueAdder implements Comparable<QueueAdder> {
        private static LinkedList<Station> rightPath = new LinkedList<>();
        private QueueAdder whoAdded;
        private Station whatAdded;
        private int time = Integer.MAX_VALUE;

        public QueueAdder(QueueAdder whoAdded, Station whatAdded) {
            this.whoAdded = whoAdded;
            this.whatAdded = whatAdded;
        }

        public QueueAdder(QueueAdder whoAdded, Station whatAdded, int time) {
            this.whoAdded = whoAdded;
            this.whatAdded = whatAdded;
            this.time = time;
        }


        public static LinkedList<Station> getRightPath(QueueAdder queueAdder) {
            rightPath.clear();
            return recursion(queueAdder);
        }

        private static LinkedList<Station> recursion(QueueAdder queueAdder) {
            rightPath.addFirst(queueAdder.whatAdded);
            if (!Objects.equals(queueAdder.whoAdded, null)) {
                recursion(queueAdder.whoAdded);
            }
            return rightPath;
        }

        @Override
        public int compareTo(QueueAdder o) {
            return Integer.compare(this.time, o.time);
        }

        @Override
        public String toString() {
            return "QueueAdder{" +
                    "whoAdded=" + whoAdded.whatAdded.getName() +
                    ", whatAdded=" + whatAdded +
                    ", time=" + time +
                    '}';
        }
    }

    private LinkedList<QueueAdder> queueAdders = new LinkedList<>();


    private LinkedList<Station> findPath() {
        if (!stationQueue.isEmpty()) {
            QueueAdder checkQueueAdder = stationQueue.poll();
            Station checkStation = checkQueueAdder.whatAdded;
            if (checkStation.equals(finishStation)) { //end-case found
                return QueueAdder.getRightPath(checkQueueAdder);
            }
            checkStation.getTransferList().forEach(st -> {
                if (!checkedStationsSet.contains(st)) {
                    Station transition = new Station("Transition to line " + st.getLine());
                    QueueAdder transitionAdder = new QueueAdder(checkQueueAdder, transition);
                    stationQueue.add(new QueueAdder(transitionAdder, st));
                }
            });
            List<Station> neighbors = new ArrayList<>();
            neighbors.addAll(checkStation.getPrevStations());
            neighbors.addAll(checkStation.getNextStations());
            neighbors.forEach(st -> {
                if (!Objects.equals(st, null) && !checkedStationsSet.contains(st)) {
                    stationQueue.add(new QueueAdder(checkQueueAdder, st));
                }
            });

            checkedStationsSet.add(checkStation);
            return findPath();
        }
        return new LinkedList<Station>();  //end-case missing
    }

    private PriorityQueue<QueueAdder> adderPriorityQueue = new PriorityQueue<>();

    public void outputShortestPath(String lineName1, String stationName1, String lineName2, String stationName2) {
        lookedUpMap.clear();
        checkedStationsSet.clear();
        Optional<MetroLine> metroLine1 = Optional.ofNullable(metroMap.get(lineName1));
        Optional<MetroLine> metroLine2 = Optional.ofNullable(metroMap.get(lineName2));
        if (metroLine1.isPresent() && metroLine2.isPresent()) {
            List<Station> station1 = metroLine1.get().getStations().stream()
                    .filter(st -> st.getName().equals(stationName1)).collect(Collectors.toList());
            List<Station> station2 = metroLine2.get().getStations().stream()
                    .filter(st -> st.getName().equals(stationName2)).collect(Collectors.toList());
            if (!station1.isEmpty() && !station2.isEmpty()) {
                startStation = station1.get(0);
                finishStation = station2.get(0);
                QueueAdder startQueueAdded = new QueueAdder(null, startStation, 0);
                adderPriorityQueue.add(startQueueAdded);
                checkedStationsSet.add(startStation);
                lookedUpMap.put(startStation, startQueueAdded);
                LinkedList<Station> pathList = findShortestPath();
                if (!pathList.isEmpty()) {
                    pathList.stream().map(st -> st.getName()).forEach(System.out::println);
                    System.out.println(String.format("Total: %d minutes in the way", finishQueueAdded.time));
                    return;
                }
            }
        }
        Controller controller = new Controller();
        controller.setCommand(new ErrorCommand(this));
        controller.executeCommand();
    }

    private QueueAdder finishQueueAdded;

    private Map<Station, QueueAdder> lookedUpMap = new HashMap<>();

    private LinkedList<Station> findShortestPath() {
        if (adderPriorityQueue.isEmpty()) {
            return QueueAdder.getRightPath(finishQueueAdded);
        }
        QueueAdder checkQueueAdder = adderPriorityQueue.poll();
        Station checkStation = checkQueueAdder.whatAdded;
        if (checkStation.equals(finishStation)) {
            finishQueueAdded = checkQueueAdder;
        }

        checkStation.getTransferList().forEach(st -> {
            if (!checkedStationsSet.contains(st)) {
                if (!lookedUpMap.containsKey(st)) {
                    Station transition = new Station("Transition to line " + st.getLine(), Station.TIME_FOR_TRANSFER);
                    QueueAdder transitionAdder
                            = new QueueAdder(checkQueueAdder, transition, transition.getTime() + checkQueueAdder.time);
                    QueueAdder neighbor = new QueueAdder(transitionAdder, st, transitionAdder.time);
                    adderPriorityQueue.add(neighbor);
                    lookedUpMap.put(st, neighbor);
                } else {
                    QueueAdder lookedQueueAdder = lookedUpMap.get(st);
                    int timeForReplace = checkQueueAdder.time + Station.TIME_FOR_TRANSFER;
                    if (lookedQueueAdder.time > timeForReplace) {
                        Station transition = new Station("Transition to line " + st.getLine(), Station.TIME_FOR_TRANSFER);
                        QueueAdder transitionAdder
                                = new QueueAdder(checkQueueAdder, transition, timeForReplace);
                        adderPriorityQueue.remove(lookedQueueAdder);
                        lookedQueueAdder.time = timeForReplace;
                        lookedQueueAdder.whoAdded = transitionAdder;
                        adderPriorityQueue.add(lookedQueueAdder);
                    }
                }
            }
        });
        List<Station> neighbors = new ArrayList<>();
        neighbors.addAll(checkStation.getPrevStations());
        neighbors.addAll(checkStation.getNextStations());
        neighbors.forEach(st -> {
            if (!Objects.equals(st, null) && !checkedStationsSet.contains(st)) {
                if (!lookedUpMap.containsKey(st)) {
                    QueueAdder neighbor;
                    if (checkStation.getNextStations().contains(st)) {
                        neighbor = new QueueAdder(checkQueueAdder, st, checkQueueAdder.time + checkStation.getTime());
                    } else {
                        neighbor = new QueueAdder(checkQueueAdder, st, checkQueueAdder.time + st.getTime());
                    }
                    adderPriorityQueue.add(neighbor);
                    lookedUpMap.put(st, neighbor);
                } else {
                    QueueAdder lookedQueueAdder = lookedUpMap.get(st);
                    int timeForReplace;
                    if (checkStation.getNextStations().contains(st)) {
                        timeForReplace = checkQueueAdder.time + checkStation.getTime();
                    } else {
                        timeForReplace = checkQueueAdder.time + st.getTime();
                    }
                    if (lookedQueueAdder.time > timeForReplace) {
                        adderPriorityQueue.remove(lookedQueueAdder);
                        lookedQueueAdder.time = timeForReplace;
                        lookedQueueAdder.whoAdded = checkQueueAdder;
                        adderPriorityQueue.add(lookedQueueAdder);
                    }
                }
            }
        });
        checkedStationsSet.add(checkStation);
        //  lookedUpMap.remove(checkStation);
        return findShortestPath();
    }
}
