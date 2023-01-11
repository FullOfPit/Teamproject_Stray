import React, {useState} from 'react';
import './App.css';
import {MapContainer, TileLayer} from "react-leaflet";
import {LatLng} from "leaflet";
import Map from "./components/Map";
import Location from "./types/location";
import location from "./types/location";
import MapSearch from "./components/MapSearch";

function App() {
    const [locations, setLocations] = useState<Location[]>([
        {name: "Neue Fische", latitude: 53.56157193989518, longitude: 9.915080727911697},
        {name: "Planten un Blomen", latitude: 53.5625456617408, longitude: 9.98188182570993},
    ]);

    return (
        <div className="App container">
            <h1>Map</h1>
            <MapSearch />
            <MapContainer className="map" center={new LatLng(53.56157193989518, 9.915080727911697)} zoom={13} scrollWheelZoom={false}>
                <TileLayer
                    attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
                    url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
                />
                <Map locations={locations} />
            </MapContainer>
        </div>
    );
}

export default App;
