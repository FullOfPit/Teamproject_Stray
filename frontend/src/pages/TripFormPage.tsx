import "./TripFormPage.css";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Trip from "../types/Trip";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Api from "../api/Api";
import Location from "../types/Location";

export default function TripFormPage() {

    const [trips, setTrips] = useState<Trip[]>([]);
    const [locations, setLocations] = useState<Location[]>([]);
    const [trip, setTrip] = useState<Trip>({title: "", tripTimestamp: "", locations: []});
    const navigate =useNavigate();

    const onClick = (() => {
        (async () => {
            const response:Trip = await Api.postTrip(trip);
            setTrips([...trips,response]);
            navigate("/");
        })();
    });

    const onAdd = (location : Location)=> {
        setLocations([...locations,location]);
        setTrip({...trip, locations: locations})
    }

    return (
        <>
            <h3>Create a trip</h3>

            <button onClick={onClick}>Save</button>

            <input placeholder={"Trip name ..."}
                   onChange={e => setTrip({...trip,title: e.target.value})}
            />

            <LocationMap locations={locations}/>

            <AddLocationForm onAdd={onAdd}/>
        </>
    );
}