import React, { useState } from 'react';

const FilterBuilder = ({ setFilters }) => {
    const [currentFilter, setCurrentFilter] = useState({
        field: '',
        operator: '',
        isInt: false,
        operant: ''
    });

    const [selectedOperators, setSelectedOperators] = useState([]);

    const [displayedFilters, setDisplayedFilters] = useState({});

    const availableFields = ["dma_id", "dma_name", "term", "week", "score", "rank", "refresh_date"];
    const availableOperators = ["=", "<", ">", "LIKE"];

    const handleFieldChange = (e) => {
        const field = e.target.value;
        const isInt = ["dma_id", "score", "rank"].includes(field);
        setCurrentFilter({ ...currentFilter, field, isInt });
    };

    const handleOperatorChange = (e) => {
        setCurrentFilter({ ...currentFilter, operator: e.target.value });
    };

    const handleOperantChange = (e) => {
        setCurrentFilter({ ...currentFilter, operant: e.target.value });
    };

    const handleAddFilter = () => {
        const key = Object.keys(displayedFilters).length + 1;
        const newFilters = { ...displayedFilters, [key]: currentFilter };
        setFilters(newFilters);
        setDisplayedFilters(newFilters);
        setCurrentFilter({
            field: '',
            operator: '',
            isInt: false,
            operant: ''
        });
    };

    const handleLogicalOperatorChange = (index, value) => {

        const updatedOperators = [...selectedOperators];
        delete updatedOperators[0]
        updatedOperators[index] = value;
        setSelectedOperators(updatedOperators);
    };

    const removeFilter = (key) => {
        const updatedFilters = { ...displayedFilters };
        delete updatedFilters[key];
        setDisplayedFilters(updatedFilters);
        setFilters(updatedFilters);
    };

    return (
        <div>
            <div>
                <h3>Filter Builder</h3>
                <select value={currentFilter.field} onChange={handleFieldChange}>
                    <option value="">Select Field</option>
                    {availableFields.map((field) => (
                        <option key={field} value={field}>{field}</option>
                    ))}
                </select>
                <select value={currentFilter.operator} onChange={handleOperatorChange} disabled={!currentFilter.field}>
                    <option value="">Select Operator</option>
                    {availableOperators.map((operator) => (
                        <option key={operator} value={operator}>{operator}</option>
                    ))}
                </select>
                <input
                    type="text"
                    value={currentFilter.operant}
                    onChange={handleOperantChange}
                    disabled={!currentFilter.operator}
                />
                <button onClick={handleAddFilter} disabled={!currentFilter.operant || !currentFilter.operator || !currentFilter.field}>
                    Add Filter
                </button>

                <div>
                    {Object.entries(displayedFilters).map(([index, filter]) => (
                        <div key={index}>
                            {parseInt(index) && displayedFilters[index - 1] && `${selectedOperators[index - 1]} `}
                            {`${filter.field} ${filter.operator} ${filter.operant}`}{' '}
                            <label>Select the logic operator of the next filter: </label>
                            <select
                                value={selectedOperators[index]}
                                onChange={(e) =>
                                    handleLogicalOperatorChange(index, e.target.value)}
                            >
                                <option value="AND">AND</option>
                                <option value="OR">OR</option>
                            </select>
                            <button onClick={() => removeFilter(index)}> X </button>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
};

export default FilterBuilder;


