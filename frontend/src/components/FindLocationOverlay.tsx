import SearchResult from "../types/openstreetmap/SearchResult";
import {ChangeEvent, useState} from "react";
import Location from "../types/Location";
import LocationMap from "./LocationMap";

export default function FindLocationOverlay({
    searchResults,
    onAdd,
    onCancel
}: {
    searchResults: SearchResult[],
    onAdd: (addLocation: Location) => void,
    onCancel: () => void
}) {
    const initialAddLocation: Location = searchResults.length > 0 ?
        {
            name: searchResults[0]?.display_name,
            latitude: searchResults[0]?.lat,
            longitude: searchResults[0]?.lon
        }
        :
        {
            name: "",
            latitude: 0,
            longitude: 0
        };
    const [addLocation, setAddLocation] = useState<Location>(initialAddLocation);

    const onChange = (e: ChangeEvent<HTMLSelectElement>) => {
        const chosen: SearchResult | undefined = searchResults.filter(place => place.place_id === Number(e.target.value)).shift();
        chosen && setAddLocation({
            name: chosen.display_name,
            latitude: chosen.lat,
            longitude: chosen.lon
        });
    }

    return (
        <>
            <p>Find Location</p>

            <select onChange={onChange}>
                {searchResults.map(place =>
                    <option key={place.place_id} value={place.place_id}>
                        {place.display_name}
                    </option>)}
            </select>

            <LocationMap locations={[addLocation]}/>

            <button onClick={onCancel}>Cancel</button>

            <button onClick={() => onAdd(addLocation)}>Add</button>
        </>
    )
}