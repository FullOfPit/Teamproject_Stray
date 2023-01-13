import {useEffect, useState} from "react";
import Trip from "../types/Trip";
import Api from "../api/Api";

export default function useGetTripsQuery() {
    const [trips, setTrips] = useState<Trip[]>([]);

    useEffect(() => {
        (async () => {
            const trips = await Api.getTrips();
            setTrips(trips);
        })();
    }, []);

    return {trips, setTrips};
}