import "./LocationMap.css";
import Location from "../types/Location";

export default function LocationMap({locations}: {
    locations: Location[]
}) {
    // this component should be the wrapper for the MapContainer of react-leaflet

    return (
        <>
            <p>I am a map for the following locations:</p>
            <ul>
                {locations.map(location => (
                    <li key={location.id ?? location.name}>{location.name} ({location.latitude}, {location.longitude})</li>
                ))}
            </ul>
        </>
    )
}