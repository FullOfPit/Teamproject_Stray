import "./AccordionItem.css";
import {MdKeyboardArrowDown, MdKeyboardArrowUp} from "react-icons/md";
import Trip from "../types/Trip";
import LocationMap from "./LocationMap";
import {Link} from "react-router-dom";

export default function AccordionItem({trip, isExpanded, onExpand, onClose}: {
    trip: Trip,
    isExpanded: boolean,
    onExpand: () => void,
    onClose: () => void,
}) {
    return (
        <div className="accordion-item">
            <header className="accordion-item-heading">
                <h4 className="accordion-item-title">
                    <Link to={`/trips/${trip.id}`}>{trip.title}</Link>
                </h4>
                {isExpanded
                    ? <MdKeyboardArrowUp className="accordion-item-handler" onClick={onClose}/>
                    : <MdKeyboardArrowDown className="accordion-item-handler" onClick={onExpand}/>
                }
            </header>
            <main className="accordion-body" data-expanded={isExpanded}>
                <LocationMap locations={trip.locations} />
            </main>
        </div>
    );
}