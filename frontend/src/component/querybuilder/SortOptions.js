import React from 'react';

const SortOptions = ({ setSortField, setSortDirection, selectedFields }) => {
    const handleFieldChange = (e) => {
        setSortField(e.target.value);
    };

    const handleDirectionChange = (e) => {
        setSortDirection(e.target.value);
    };

    return (
        <div>
            <h3>Sort Options</h3>
            <label>
                Select Field:
                <select onChange={handleFieldChange}>
                    <option value="">Select Field</option>
                    {selectedFields.map((field) => (
                        <option key={field} value={field}>
                            {field}
                        </option>
                    ))}
                </select>
            </label>
            <label>
                Select Direction:
                <select onChange={handleDirectionChange}>
                    <option value="ASC">Ascending</option>
                    <option value="DESC">Descending</option>
                </select>
            </label>
        </div>
    );
};

export default SortOptions;
