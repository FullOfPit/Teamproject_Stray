import axios from "axios";
import Trip from "../types/Trip";
import Location from "../types/Location";

const client = axios.create({
    baseURL: "/api/trips"
});

module Api {
    export async function getTrips(): Promise<Trip[]> {
        const response = await client.get("");

        return response.data;
    }

    export async function postTrip(trip: Trip): Promise<Trip> {
        const response = await client.post("", trip);

        return response.data
    }

    export async function getTrip(id: string): Promise<Trip> {
        const response = await client.get(`/${id}`);

        return response.data;
    }

    export async function updateTrip(trip: Trip): Promise<Trip> {
        const response = await client.put(`/${trip.id}`, trip);

        return response.data;
    }

    export async function deleteTrip(id: string): Promise<Trip> {
        const response = await client.delete(`/${id}`);

        return response.data;
    }

    export async function getShortestPathForTrip(id: string): Promise<Location[]> {
        const response = await client.get(`/${id}/shortest-path`);

        return response.data;
    }
}

export default Api;
