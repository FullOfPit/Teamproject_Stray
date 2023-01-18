import L from "leaflet";
import {createControlComponent} from "@react-leaflet/core";
import "leaflet-routing-machine";

const createRoutineMachineLayer = () => {
    return L.Routing.control({
        waypoints: [
            L.latLng(33.52001088075479, 36.26829385757446),
            L.latLng(33.50546582848033, 36.29547681726967)
        ],
        lineOptions: {
            styles: [{color: "#6FA1EC", weight: 4}],
            extendToWaypoints: false,
            missingRouteTolerance: 0
        },
        show: false,
        addWaypoints: false,
        routeWhileDragging: true,
        fitSelectedRoutes: true,
        showAlternatives: false
    });
};

const RoutingMachine = createControlComponent(createRoutineMachineLayer);

export default RoutingMachine;