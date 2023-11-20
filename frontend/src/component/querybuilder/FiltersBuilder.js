import React, {useState, useEffect} from 'react';

const FilterBuilder = ({setFilters, filters, setSelectedOperators, selectedOperators}) => {
    const [currentFilter, setCurrentFilter] = useState({
        field: '',
        operator: '',
        isInt: false,
        operant: ''
    });

    const [showLogicalOperatorButton, setShowLogicalOperatorButton] = useState(false);
    const [showLogicalOperator, setShowLogicalOperator] = useState(false);
    const [enableFilterCreation, setEnableFilterCreation] = useState(true);
    const [showSaveButton, setShowSaveButton] = useState(true);


    const availableFields = ["dma_id", "dma_name", "term", "week", "score", "rank", "refresh_date"];
    const availableOperators = ["=", "<", ">", "LIKE"];

    const handleFieldChange = (e) => {
        const field = e.target.value;
        const isInt = ["dma_id", "score", "rank"].includes(field);
        setCurrentFilter({...currentFilter, field, isInt});
    };

    const handleOperatorChange = (e) => {
        setCurrentFilter({...currentFilter, operator: e.target.value});
    };

    const handleOperantChange = (e) => {
        setCurrentFilter({...currentFilter, operant: e.target.value});
    };

    const handleAddFilter = () => {
        const key = Object.keys(filters).length + 1;
        const newFilter = {...currentFilter};
        setFilters({...filters, [key]: newFilter});
        setCurrentFilter({
            field: '',
            operator: '',
            isInt: false,
            operant: ''
        });

        setShowLogicalOperatorButton(true);
        setEnableFilterCreation(false);
        setShowSaveButton(true);
    };

    const handleSaveFilters = () => {

        setFilters(filters);
        setSelectedOperators(selectedOperators);
        setEnableFilterCreation(false);
        setShowLogicalOperatorButton(false);
        setEnableFilterCreation(false);
        setShowSaveButton(false);
    };

    const handleLogicalOperatorChange = (value) => {
        setSelectedOperators([value]);
        setEnableFilterCreation(true);
        setShowLogicalOperator(false);
    };

    const removeFilter = (key) => {
        const updatedFilters = {...filters};
        delete updatedFilters[key];
        setFilters(updatedFilters);
    };

    const handleSelectedOperators = () => {
        setShowLogicalOperator(true);
        setShowLogicalOperatorButton(false)
    };


    useEffect(() => {
        setFilters(filters);
    }, [filters, setFilters]);

    return (
        <div>
            <div>
                <h3>Filter Builder</h3>
                <select value={currentFilter.field} onChange={handleFieldChange} disabled={!enableFilterCreation}>
                    <option value="">Select Field</option>
                    {availableFields.map((field) => (
                        <option key={field} value={field}>{field}</option>
                    ))}
                </select>
                <select value={currentFilter.operator} onChange={handleOperatorChange}
                        disabled={!currentFilter.field || !enableFilterCreation}>
                    <option value="">Select Operator</option>
                    {availableOperators.map((operator) => (
                        <option key={operator} value={operator}>{operator}</option>
                    ))}
                </select>
                <input
                    type="text"
                    value={currentFilter.operant}
                    onChange={handleOperantChange}
                    disabled={!currentFilter.operator || !enableFilterCreation}
                />
                <button onClick={handleAddFilter}
                        disabled={!currentFilter.operant || !currentFilter.operator || !currentFilter.field || !showSaveButton}>
                    Add Filter
                </button>

                <button onClick={handleSelectedOperators}
                        disabled={!showLogicalOperatorButton}>
                    Add Logic Operator
                </button>
                <p> If don´t want to add another filter, please save. </p>
                <button onClick={handleSaveFilters} disabled={!showSaveButton}>
                    Save Filters
                </button>
                {showLogicalOperator && (
                    <div>
                        <label>Select the logic operator for all filters: </label>
                        <select
                            value={selectedOperators[0]}
                            onChange={(e) => handleLogicalOperatorChange(e.target.value)}
                        >
                            <option value="">Select an option</option>
                            <option value="OR">OR</option>
                            <option value="AND">AND</option>
                        </select>
                    </div>
                )}

                <div>
                    {Object.entries(filters).map(([index, filter]) => (
                        <div key={index}>
                            {`${filter.field} ${filter.operator} ${filter.operant}`}{' '}
                            <button onClick={() => removeFilter(index)}> X</button>
                        </div>
                    ))}
                </div>
            </div>
        </div>
    );
}

export default FilterBuilder;
