import React, { useState } from 'react';

const GroupBySelector = ({ onGroupBy, setGroupBy }) => {
    const [isGroupingEnabled, setIsGroupingEnabled] = useState(false);

    const handleGroupBy = () => {
        setGroupBy(onGroupBy);
    };

    const handleCheckboxChange = (e) => {
        const { value } = e.target;
        setGroupBy((prevFields) =>
            prevFields.includes(value)
                ? prevFields.filter((field) => field !== value)
                : [...prevFields, value]
        );
    };

    return (
        <div>
            <input
                type="checkbox"
                checked={isGroupingEnabled}
                onChange={() => setIsGroupingEnabled(!isGroupingEnabled)}
            />
            <label htmlFor="groupCheckbox">Group By</label>
            {isGroupingEnabled && (
                <div>
                    <p> Select all that you want to group by</p>
                    {['dma_id', 'dma_name', 'week', 'rank', 'refresh_date', 'score', 'term'].map((field) => (
                        <div key={field}>
                            <input
                                type="checkbox"
                                id={field}
                                value={field}
                                checked={onGroupBy.includes(field)}
                                onChange={handleCheckboxChange}
                            />
                            <label htmlFor={field}>{field}</label>
                        </div>
                    ))}
                    <button onClick={handleGroupBy}>Group</button>
                </div>
            )}
        </div>
    );
};

export default GroupBySelector;
