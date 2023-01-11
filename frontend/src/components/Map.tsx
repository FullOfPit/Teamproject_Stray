import {Marker, Popup, Tooltip, useMapEvents} from "react-leaflet";
import React, {useState} from "react";
import {LatLng, LeafletMouseEvent} from "leaflet";
import Location from "../types/location";

export default function Map({ locations }: {
    locations: Location[]
}) {

    /* Add Marker on Click
     * Problem: How to add name to Location?
     */
    // const map = useMapEvents({
    //     click: (event: LeafletMouseEvent) => {
    //         const position: LatLng = event.latlng
    //         const location = {name: "", latitude: position.lat, longitude: position.lng}
    //         setLocations([...locations, location]);
    //     }
    // });

    return (
        <>
            {locations.map(location => (
                <Marker key={location.name} position={new LatLng(location.latitude, location.longitude)}>
                    <Popup>
                        <p><strong>Popup</strong></p>
                        <p>{location.name}</p>
                    </Popup>
                    {/*<Tooltip>*/}
                    {/*    <p><strong>Tooltip</strong></p>*/}
                    {/*    <p>{location.name}</p>*/}
                    {/*</Tooltip>*/}
                </Marker>
            ))}
        </>
    )
}