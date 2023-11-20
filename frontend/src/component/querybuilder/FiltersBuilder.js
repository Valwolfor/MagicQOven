import React, { useState } from 'react';

const FilterBuilder = ({ setFilters, filters }) => {
    // State to manage the current filter being constructed
    const [currentFilter, setCurrentFilter] = useState({
        field: '',
        operator: '',
        isInt: false,
        operant: ''
    });

    // Available fields and operators for the filter
    const availableFields = ["dma_id", "dma_name", "term", "week", "score", "rank", "refresh_date"];
    const availableOperators = ["=", "<", ">", "LIKE"];

    // Handles the change when selecting a field for the filter
    const handleFieldChange = (e) => {
        const field = e.target.value;
        // Determine if the selected field requires an integer (true for dma_id, score, rank)
        const isInt = ["dma_id", "score", "rank"].includes(field);
        // Update the current filter state with the selected field and its integer requirement
        setCurrentFilter({ ...currentFilter, field, isInt });
    };

    // Handles the change when selecting an operator for the filter
    const handleOperatorChange = (e) => {
        setCurrentFilter({ ...currentFilter, operator: e.target.value });
    };

    // Handles the change when entering the operant for the filter
    const handleOperantChange = (e) => {
        setCurrentFilter({ ...currentFilter, operant: e.target.value });
    };

    // Adds the current filter to the list of filters and resets the current filter state
    const handleAddFilter = () => {
        setFilters([...filters, currentFilter]);
        setCurrentFilter({
            field: '',
            operator: '',
            isInt: false,
            operant: ''
        });
    };

    return (
        <div>
            <h3>Filter Builder</h3>
            {/* Dropdown to select the field for the filter */}
            <select value={currentFilter.field} onChange={handleFieldChange}>
                <option value="">Select Field</option>
                {availableFields.map((field) => (
                    <option key={field} value={field}>{field}</option>
                ))}
            </select>
            {/* Dropdown to select the operator for the filter */}
            <select value={currentFilter.operator} onChange={handleOperatorChange} disabled={!currentFilter.field}>
                <option value="">Select Operator</option>
                {availableOperators.map((operator) => (
                    <option key={operator} value={operator}>{operator}</option>
                ))}
            </select>
            {/* Input field to enter the operant for the filter */}
            <input
                type="text"
                value={currentFilter.operant}
                onChange={handleOperantChange}
                disabled={!currentFilter.operator}
            />
            {/* Button to add the current filter */}
            <button onClick={handleAddFilter} disabled={!currentFilter.operant}>
                Add Filter
            </button>
        </div>
    );
};

export default FilterBuilder;
