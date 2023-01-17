import "./TripFormPage.css";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Trip from "../types/Trip";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Api from "../api/Api";
import Location from "../types/Location";

export default function TripFormPage() {

    const [locations, setLocations] = useState<Location[]>([]);
    const [trip, setTrip] = useState<Trip>({title: "", tripTimestamp: "", locations: []});
    const navigate =useNavigate();

    const onSave = (() => {
        (async () => {
            await Api.postTrip(trip);
            navigate("/");
        })();
    });

    const onAdd = (location : Location)=> {
        setLocations([...locations,location]);
        setTrip({...trip, locations: locations})
    }

    return (
        <>
            <header>
                <h3>Create a trip</h3>
            </header>

            <main>
                <button onClick={trip.title && trip.locations
                    ? onSave
                    : () => alert("You do not have a trip title or trip locations set")
                }>Save</button>

                <input placeholder={"Trip title ..."}
                       onChange={e => setTrip({...trip,title: e.target.value})}
                />

                <LocationMap locations={locations}/>
            </main>

            <footer className={"trip-form-page-footer"}>
                <div className={"fixed fixed-bottom"}>
                    <AddLocationForm onAdd={onAdd}/>
                </div>
            </footer>
        </>
    );
}