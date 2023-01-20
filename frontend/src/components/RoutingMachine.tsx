import L, {ControlOptions, latLng} from "leaflet";
import {createControlComponent} from "@react-leaflet/core";
import "leaflet-routing-machine";


const createRoutineMachineLayer = ({routes}:{routes:L.LatLng[]}) => {

    return L.Routing.control({
        waypoints: routes,
        lineOptions: {
            styles: [{color: "#6FA1EC", weight: 4}],
            extendToWaypoints: false,
            missingRouteTolerance: 0
        },

    });
};

const RoutingMachine = createControlComponent(createRoutineMachineLayer);

export default RoutingMachine;




/*
[
    L.latLng(33.52001088075479, 36.26829385757446),
    L.latLng(33.50546582848033, 36.29547681726967)
]
 */
