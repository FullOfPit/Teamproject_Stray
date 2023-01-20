import "./LocationMap.css";
import Location from "../types/Location";
import {MapContainer, TileLayer} from "react-leaflet";
import LocationMarkerGroup from "./LocationMarkerGroup";
import Routing from "./RoutingMachine";

export default function LocationMap({
    locations,
    routing
}:{
    locations: Location[]
    routing:boolean
}) {

    return (
        <>
            <MapContainer className="map-container" center={[53.56159742972676, 9.915145101001647]} zoom={10}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />

                {routing ?
                    <Routing locations={locations}/>
                    :
                    <LocationMarkerGroup locations={locations}/>}

            </MapContainer>
        </>
    )
}