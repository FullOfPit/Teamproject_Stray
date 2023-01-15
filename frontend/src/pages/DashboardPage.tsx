import "./DashboardPage.css";
import {Link} from "react-router-dom";

export default function DashboardPage() {
    // if not logged in, then navigate.to LoginPage?

    // get all trips from API
    // if no trips yet, navigate.to TripFormPage?

    // show all trips in list of accordions
    // on click go to Detail page
    // on "open accordion" -> show map

    return (
        <>
            <h1>DashboardPage</h1>
            <Link to="/trips/63c3e23cbbdcc97132521ba0">Kultour</Link>
            <Link to="/trips/63c4158cbbdcc97132521ba4">Pubcrawl</Link>
            <Link to="/trips/63c3f83dbbdcc97132521ba1">Sauftour</Link>
        </>
    );
}