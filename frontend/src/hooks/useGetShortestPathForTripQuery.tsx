import {useState} from "react";
import Location from "../types/Location";
import Trip from "../types/Trip";
import Api from "../api/Api";

export default function useGetShortestPathForTripQuery(initialState: Location[]) {
    const [shortestPath, setShortestPath] = useState<Location[]>(initialState);

    const getShortestPathForTripQuery = async (trip: Trip) => {
        const shortestPath = await Api.getShortestPathForTrip(trip.id);
        setShortestPath(shortestPath);
    }

    return {shortestPath, setShortestPath, getShortestPathForTripQuery};
}