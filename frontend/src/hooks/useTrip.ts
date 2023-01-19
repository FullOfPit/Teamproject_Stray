import {useEffect, useState} from "react";
import Trip from "../types/Trip";
import Api from "../api/Api";
import {isAxiosError} from "axios";
import Location from "../types/Location";

const initialState: Trip = {
    id: "",
    title: "",
    tripTimestamp: "",
    locations: []
}

export default function useTrip(id: string) {
    const [trip, setTrip] = useState<Trip>(initialState);
    const [notFound, setNotFound] = useState<boolean>();

    useEffect(() => {
        (async () => {
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

    const deleteTripQuery = async (trip: Trip) => {
        if (!trip.id) {
            return;
        }
        
        await Api.deleteTrip(trip.id);
    };

    const getShortestPathForTripQuery = async (trip: Trip) => {
        if (!trip.id) {
            return;
        }

        const shortestPath = await Api.getShortestPathForTrip(trip.id);
        setTrip({...trip, locations: shortestPath});
    }

    const removeLocationFromTrip = (deletedLocation: Location) => {
        const updatedTrip = {...trip, locations: trip.locations.filter(location => location.id !== deletedLocation.id)}
        setTrip(updatedTrip);
    }

    const addLocationToTrip = (addedLocation: Location) => {
        const updatedTrip = {...trip, locations: [...trip.locations, addedLocation]}
        setTrip(updatedTrip);
    }

    return {
        trip,
        notFound,
        updateTripQuery,
        deleteTripQuery,
        getShortestPathForTripQuery,
        addLocationToTrip,
        removeLocationFromTrip
    };
}