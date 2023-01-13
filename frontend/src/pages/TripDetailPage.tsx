import "./TripDetailPage.css";
import {useParams} from "react-router-dom";

export default function TripDetailPage() {
    // get trip id from url
    const {id} = useParams<{ id: string }>();

    // request trip from API
    // if request does not exist -> TBD?

    // title is input field ("border:none") -> can be edited (click on "save" necessary to save changes)
    // show delete button -> on click -> request to DELETE Endpoint and navigate bach to DashboardPage
    // show save button -> PUT API endpoint
    // show map with location markers
    // show list of locations
    //      each item has a "trash icon" (see react-icons) on it -> on click remove location from trip (click on "save" necessary to save changes)
    // AddLocationForm -> if location is added -> add to trip (click on "save" necessary to save changes)

    return (
        <>
            <h1>TripDetailPage for Trip {id}</h1>
        </>
    )
}