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
    }, [id]);

    const updateTripQuery = async (trip: Trip) => {
        const updatedTrip = await Api.updateTrip(trip);
        setTrip(updatedTrip);
    }

    const deleteTripQuery = async (id: string) => {
        await Api.deleteTrip(id);
    };

    const removeLocationFromTrip = (locationId: string) => {
        const updatedTrip = {...trip, locations: trip.locations.filter(location => location.id !== locationId)}
        setTrip(updatedTrip);
    }

    return {trip, notFound, updateTripQuery, deleteTripQuery, removeLocationFromTrip};
}