import {useState} from "react";

const SortOptions = ({ setSortField, setSortDirection, selectedFields }) => {
    const [selectedField, setSelectedField] = useState("Select Field");

    const handleFieldChange = (e) => {
        const selectedValue = e.target.value;
        setSelectedField(selectedValue);
        setSortField(selectedValue);

        setSortDirection("");
    };

    const handleDirectionChange = (e) => {
        setSortDirection(e.target.value);
    };

    return (
        <div>
            <h3>Sort Options</h3>
            <label>
                Select Field:
                <select onChange={handleFieldChange} value={selectedField}>
                    <option value="Select Field" disabled>
                        Select Field
                    </option>
                    {selectedFields.map((field) => (
                        <option key={field} value={field}>
                            {field}
                        </option>
                    ))}
                </select>
            </label>
            <label>
                Select Direction:
                <select
                    onChange={handleDirectionChange}
                    disabled={selectedField === "Select Field"}
                >
                    <option value="ASC">Ascending</option>
                    <option value="DESC">Descending</option>
                </select>
            </label>
        </div>
    );
};

export default SortOptions;
