import "./TripDetailPage.css";
import {useParams} from "react-router-dom";
import useGetTripByIdQuery from "../hooks/useGetTripByIdQuery";
import LocationMap from "../components/LocationMap";
import AddLocationForm from "../components/AddLocationForm";
import Error from "../components/Error";

type TripDetailParams = {
    id: string,
}

export default function TripDetailPage() {
    // get trip id from url
    const {id} = useParams<keyof TripDetailParams>() as TripDetailParams;
    const {trip, notFound} = useGetTripByIdQuery(id);

    // title is input field ("border:none") -> can be edited (click on "save" necessary to save changes)
    // show delete button -> on click -> request to DELETE Endpoint and navigate bach to DashboardPage
    // show save button -> PUT API endpoint
    // show map with location markers
    // show list of locations
    //      each item has a "trash icon" (see react-icons) on it -> on click remove location from trip (click on "save" necessary to save changes)
    // AddLocationForm -> if location is added -> add to trip (click on "save" necessary to save changes)

    if (notFound) {
        return <Error message="Trip not found." link={{text: "Show all trips", to: "/"}}/>
    }

    return (
        <>
            <header>
                <h1>{trip.title}</h1>
                <button>Save</button>
                <button>Delete</button>
            </header>
            <main>
                {trip.locations.length > 0
                    ? <>
                        <LocationMap/>
                        <ul className="locations-list">
                            {trip.locations.map(location => (
                                <li key={location.id}>{location.name}</li>
                            ))}
                        </ul>
                        <button>Stray!</button>
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