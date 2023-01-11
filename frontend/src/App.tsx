import React from 'react';
import './App.css';
import {MapContainer, Marker, Popup, TileLayer, Tooltip} from "react-leaflet";
import {LatLng, LatLngExpression} from "leaflet";

function App() {
    // @ToDo make type?
    const locations = [
        {name: "Neue Fische", position: new LatLng(53.56157193989518, 9.915080727911697)},
        {name: "Planten un Blomen", position: new LatLng(53.5625456617408, 9.98188182570993)},
    ]

    return (
        <div className="App container">
            <h1>Map</h1>
            <MapContainer className="map" center={locations[0].position} zoom={13} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                {locations.map(location => (
                    <Marker position={location.position}>
                        <Popup>
                            <p><strong>Popup</strong></p>
                            <p>{location.name}</p>
                        </Popup>
                        <Tooltip>
                            <p><strong>Tooltip</strong></p>
                            <p>{location.name}</p>
                        </Tooltip>
                    </Marker>
                ))}
            </MapContainer>
        </div>
    );
}

export default App;
