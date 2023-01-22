import L from "leaflet";
import {createControlComponent} from "@react-leaflet/core";
import "leaflet-routing-machine";
import Location from "../types/Location";
import "./RoutingMachine.css";

export default function Routing({locations}: {
    locations: Location[]
}) {
    const waypoints = locations.map(l => new L.LatLng(l.latitude, l.longitude));

    const RoutingMachine = createControlComponent(() => L.Routing.control({
        waypoints: waypoints,
        lineOptions: {
            styles: [{color: "#6FA1EC", weight: 4}],
            extendToWaypoints: false,
            missingRouteTolerance: 0
        },
        showAlternatives: false,
    }));

    return (
        <RoutingMachine/>
    )
}
