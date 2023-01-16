import "./DashboardPage.css";
import {useNavigate} from "react-router-dom";
import Accordion from "../components/Accordion";
import useGetTripsQuery from "../hooks/useGetTripsQuery";

export default function DashboardPage() {
    // if not logged in, then navigate.to LoginPage?

    const navigate = useNavigate();
    const {trips} = useGetTripsQuery();

    if (trips.length === 0) {
        navigate("/trips/create");
    }

    return (
        <div className="dashboard-container">
            <h1>Planned Trips</h1>

            <Accordion trips={trips}/>
        </div>
    );
}