import {useEffect, useState} from "react";
import Trip from "../types/Trip";
import Api from "../api/Api";
import {isAxiosError} from "axios";

const initialState: Trip = {
    id: "",
    title: "",
    tripTimestamp: "",
    locations: []
}

export default function useGetTripByIdQuery(id: string) {
    const [trip, setTrip] = useState<Trip>(initialState);
    const [notFound, setNotFound] = useState<boolean>();

    useEffect(() => {
        (async() => {
            try {
                const trip = await Api.getTrip(id);
                setTrip(trip);
            } catch (e) {
                if (isAxiosError(e) && e.response?.status === 404) {
                    setNotFound(true);
                }
            }
        })();
    }, []);

    return {trip, notFound};
}