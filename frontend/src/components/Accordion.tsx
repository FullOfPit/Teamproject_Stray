import "./Accordion.css";
import Trip from "../types/Trip";
import AccordionItem from "./AccordionItem";
import {useState} from "react";

export default function Accordion({trips}: {
    trips: Trip[]
}) {
    const [expandedIndex, setExpandedIndex] = useState<number>(0);

    return (
        <div className="accordion">
            {trips.map((trip: Trip, index: number) => (
                <AccordionItem
                    key={trip.id}
                    trip={trip}
                    isExpanded={index === expandedIndex}
                    onExpand={() => setExpandedIndex(index)}
                    onClose={() => setExpandedIndex(-1)}
                />
            ))}
        </div>
    );
}