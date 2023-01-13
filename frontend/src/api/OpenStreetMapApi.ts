import axios from "axios";
import SearchResult from "../types/openstreetmap/SearchResult";

const client = axios.create({
    baseURL: "https://nominatim.openstreetmap.org"
});

module OpenStreetMapApi {
    export async function search(query: string): Promise<SearchResult[]> {
        const response = await client.get("/search", {
            params: {
                q: query,
                "accept-language": "de-de",
                format: "jsonv2"
            }
        });

        return response.data;
    }
}

export default OpenStreetMapApi;
