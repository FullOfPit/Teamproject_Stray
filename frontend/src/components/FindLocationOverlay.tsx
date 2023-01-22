import SearchResult from "../types/openstreetmap/SearchResult";
import {ChangeEvent, useState} from "react";
import Location from "../types/Location";
import LocationMap from "./LocationMap";
import "./FindLocationOverlay.css";
import Button from "react-bootstrap/Button";

export default function FindLocationOverlay({
    searchResults,
    onAdd,
    onCancel
}:{
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
    const [selected, setSelected] = useState<SearchResult | undefined>(searchResults[0]);

    const onChange = (e: ChangeEvent<HTMLSelectElement>) => {
        setSelected(searchResults.filter(place => place.place_id === Number(e.target.value)).shift());
        selected && setAddLocation({
            name: selected.display_name,
            latitude: selected.lat,
            longitude: selected.lon
        });
    }
    console.log(selected);
    console.log(addLocation);
    return (
        <>
            <div className={"overlay-container"}>
                <div>
                    <h3>Find Location</h3>
                </div>

                <div className={"select-list"}>
                    <select onChange={onChange}>
                        {searchResults.map(place =>
                            <option key={place.place_id} value={place.place_id}>
                                {place.display_name}
                            </option>)}
                    </select>
                </div>

                <div>
                    <LocationMap locations={[addLocation]} routing={false}/>
                </div>

                <div className={"buttons-container"}>
                    <Button variant={"light"} onClick={onCancel}>Cancel</Button>
                    <Button variant={"light"} onClick={() => onAdd(addLocation)}>Add</Button>
                </div>
            </div>
        </>
    )
}