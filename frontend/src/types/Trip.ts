import Location from "./Location";

type Trip = {
    id: string,
    title: string,
    tripTimestamp: string,
    locations: Location[]
}

export default Trip;