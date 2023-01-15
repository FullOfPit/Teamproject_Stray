import "./LocationList.css";
import {BsFillTrashFill} from "react-icons/bs";
import Location from "../types/Location";

export default function LocationList({locations, onLocationDelete}: {
    locations: Location[],
    onLocationDelete: (locationId: string) => void;
}) {
    return (
        <ul className="location-list">
            {locations.map(location => (
                <li key={location.id} className="location-list-item">
                    <p>{location.name}</p>
                    <BsFillTrashFill onClick={() => onLocationDelete(location.id)}/>
                </li>
            ))}
        </ul>
    )
}