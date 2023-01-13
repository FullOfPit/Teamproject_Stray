import "./DashboardPage.css";
import {useEffect, useState} from "react";
import Trip from "../types/Trip";
import Api from "../api/Api";
import {useNavigate} from "react-router-dom";
import Accordion from "../components/Accordion";

export default function DashboardPage() {
    // if not logged in, then navigate.to LoginPage?

    const navigate = useNavigate();
    const [trips, setTrips] = useState<Trip[]>([]);

    useEffect(() => {
        (async () => {
            const trips = await Api.getTrips();
            setTrips(trips);
        })();
    }, []);

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