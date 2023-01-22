import "./TripFormPage.css";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Trip from "../types/Trip";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import Api from "../api/Api";
import Location from "../types/Location";
import Form from "react-bootstrap/Form";
import InputGroup from "react-bootstrap/InputGroup";
import Button from "react-bootstrap/Button";

export default function TripFormPage() {

    const [trip, setTrip] = useState<Trip>({title: "", tripTimestamp: "", locations: []});
    const navigate = useNavigate();

    const onSave = (() => {
        (async () => {
            const response = await Api.postTrip(trip);
            navigate("/");
        })();
    });

    const onLocationAdd = (location: Location) => setTrip({...trip, locations: [...trip.locations, location]});

    return (
        <>
            <header className={"title"}>
                <h1>Create a trip</h1>
            </header>

            <main>
                <div>
                    <InputGroup>
                        <Form.Control
                            placeholder={"Trip Title "}
                            onChange={e => setTrip({...trip, title: e.target.value})}/>

                        <Button variant={"outline-light"} onClick={trip.title && trip.locations
                            ? onSave
                            : () => alert("You do not have a trip title or trip locations set")
                        }>Save</Button>
                   </InputGroup>
                </div>

                <LocationMap locations={trip.locations} routing={false}/>
            </main>

            <footer className={"add-form-page"}>
                    <AddLocationForm onAdd={onLocationAdd}/>
            </footer>
        </>
    );
}