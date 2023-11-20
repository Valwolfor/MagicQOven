import React, {useState} from 'react';
import LimitSelector from "./LimitSelector";
import FiltersBuilder from "./FiltersBuilder";
import FieldSelector from "./FieldSelector";
import SortOptions from "./SortOptions";
import Dashboard from "../../pages/Dashboard";
import GroupBySelector from "./GroupBySelector";


const QueryBuilder = () => {
    const [selectedFields, setSelectedFields] = useState([]);
    const [filters, setFilters] = useState({});
    const [sortField, setSortField] = useState('');
    const [sortDirection, setSortDirection] = useState('ASC');
    const [selectedOperators, setSelectedOperators] = useState([]);
    const [onGroupBy, setGroupBy] = useState([]);
    const [limit, setLimit] = useState(100);
    const [chartData, setChartData] = useState([]);

    const buildQueryParameters = () => {
        const queryParameters = {
            filters: filters,
            selectedFields: selectedFields,
            sortField: sortField,
            sortDirection: sortDirection,
            selectedOperators: selectedOperators,
            groupedFields: onGroupBy,
            limit: limit,
        };
        return queryParameters;
    };
    const handleQuerySubmit = async () => {
        const queryParameters = buildQueryParameters();
        const username = 'witcher.valwolfor';
        const password = 'abrakadabra777';
        const basicAuth = 'Basic ' + btoa(username + ':' + password);


        const handleError = (error) => {
            console.error('Error retrieving the data:', error);
        };

        const response = await fetch('http://localhost:8085/bigquery/dynamic', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': basicAuth
            },
            body: JSON.stringify(queryParameters)
        })
            .then(response => response.json())
            .then(data => processData(data))
            .catch(error => handleError(error));


    };

    const processData = (data) => {
        setChartData(data);
    };

    return (
        <div>
            <h1>Magic Qoven</h1>
            <h3>A Query Builder to the Dataset of Google Trends.</h3>
            <p>Here a litter description of the partition that we will use.</p>
            <ul>
                <li> term - STRING - the human readable identifier for a term.</li>
                <li> dma_name - STRING - stores the full text name of the DMA.</li>
                <li> dma_id - INT - 3 digit numerical ID used to identify a DMA.</li>
                <li> week - DATE - first day of the week that appears the term with DMA and score.</li>
                <li> refresh_date - DATE - date when the new set of term, score, and DMA combination was added.</li>
                <li> score - INT - index from 0-100 that denotes how popular this term was for a DMA during the current
                    date.
                </li>
                <li> rank - INT - numeric representation of where the term falls in comparison to the other top terms.
                </li>
            </ul>
            <div>
                <p>
                    Now select the correct ingredients for make the powerfull <strong>Spells of Charts</strong>.
                </p>
            </div>
            <FieldSelector setSelectedFields={setSelectedFields} selectedFields={selectedFields}/>
            <FiltersBuilder setFilters={setFilters} filters={filters}
                            setSelectedOperators={setSelectedOperators} selectedOperators={selectedOperators}/>
            <GroupBySelector setGroupBy={setGroupBy} onGroupBy={onGroupBy}/>
            <SortOptions setSortField={setSortField} setSortDirection={setSortDirection}
                         selectedFields={selectedFields}/>
            <LimitSelector setLimit={setLimit} limit={limit}/>
            <button onClick={handleQuerySubmit}>Send Query</button>
            <div>
                The magic born here!
            </div>
            <div>
                {chartData.length === 0 ? (
                    <p>The data retrieved is empty or the spells are charging </p>
                ) : (
                    <Dashboard chartData={chartData}></Dashboard>
                )}
            </div>
        </div>
    );
};

export default QueryBuilder;
