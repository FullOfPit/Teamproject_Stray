import "./TripDetailPage.css";
import {useNavigate, useParams} from "react-router-dom";
import useTrip from "../hooks/useTrip";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Error from "../components/Error";
import LocationList from "../components/LocationList";
import Trip from "../types/Trip";
import Api from "../api/Api";

type TripDetailParams = {
    id: string,
}

/**
 * @ToDo title is input field ("border:none") -> can be edited (click on "save" necessary to save changes)
 */
export default function TripDetailPage() {
    const navigate = useNavigate();

    const {id} = useParams<keyof TripDetailParams>() as TripDetailParams;
    const {trip, notFound, updateTripQuery, deleteTripQuery, removeLocationFromTrip, setLocationsOnTrip} = useTrip(id);

    if (notFound) {
        return <Error message="Trip not found." link={{text: "Show all trips", to: "/"}}/>
    }

    const getShortestPathForTripQuery = async (trip: Trip) => {
        const shortestPath = await Api.getShortestPathForTrip(trip.id);
        setLocationsOnTrip(shortestPath);
    }

    const onDelete = async (trip: Trip) => {
        await deleteTripQuery(trip);
        navigate("/");
    }

    return (
        <>
            <header className="detail-page-header sticky sticky-top">
                <h1>{trip.title}</h1>
                <div className="detail-page-actions">
                    <button onClick={() => updateTripQuery(trip)}>Save</button>
                    <button onClick={() => onDelete(trip)}>Delete</button>
                </div>
            </header>
            <main>
                {trip.locations.length > 0
                    ? <>
                        {/* @ToDo pass locations to map */}
                        <LocationMap/>
                        <LocationList locations={trip.locations} onLocationDelete={removeLocationFromTrip}/>
                        <button onClick={() => getShortestPathForTripQuery(trip)}>Stray!</button>
                    </>
                    : <div className="message-container">
                        <p>You haven't added any locations yet</p>
                    </div>
                }
            </main>
            <footer>
                <AddLocationForm/>
            </footer>
        </>
    )
}