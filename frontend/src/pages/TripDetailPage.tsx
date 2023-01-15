import "./TripDetailPage.css";
import {useNavigate, useParams} from "react-router-dom";
import useTrip from "../hooks/useTrip";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Error from "../components/Error";
import LocationList from "../components/LocationList";
import useGetShortestPathForTripQuery from "../hooks/useGetShortestPathForTripQuery";
import Trip from "../types/Trip";

type TripDetailParams = {
    id: string,
}

export default function TripDetailPage() {
    const navigate = useNavigate();

    const {id} = useParams<keyof TripDetailParams>() as TripDetailParams;
    const {trip, notFound, updateTripQuery, deleteTripQuery, removeLocationFromTrip} = useTrip(id);
    const {shortestPath, getShortestPathForTripQuery} = useGetShortestPathForTripQuery();

    // @ToDo title is input field ("border:none") -> can be edited (click on "save" necessary to save changes)

    if (notFound) {
        return <Error message="Trip not found." link={{text: "Show all trips", to: "/"}}/>
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
                        <LocationList locations={shortestPath.length !== 0 ? shortestPath : trip.locations} onLocationDelete={removeLocationFromTrip}/>
                        <button onClick={() => getShortestPathForTripQuery(trip)}>Stray!</button>
                    </>
                    : <div className="message-container">
                        <p>You haven't added any locations yet</p>
                    </div>
                }

                <p>
                    {/* @ToDo remove me */}
                    This paragraph is only for demonstration of fixed header and footer.<br/>
                    It can be removed if map is ready<br/><br/>
                    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
                    Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.
                </p>
            </main>
            <footer>
                <AddLocationForm/>
            </footer>
        </>
    )
}