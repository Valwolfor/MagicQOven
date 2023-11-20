import React, {useState} from 'react';
import LimitSelector from "./LimitSelector";
import FiltersBuilder from "./FiltersBuilder";
import FieldSelector from "./FieldSelector";
import SortOptions from "./SortOptions";


const QueryBuilder = () => {
    const [selectedFields, setSelectedFields] = useState([]);
    const [filters, setFilters] = useState([]);
    const [sortField, setSortField] = useState('');
    const [sortDirection, setSortDirection] = useState('ASC');
    const [selectedOperators] = useState([]);
    const [groupedFields] = useState([]);
    const [limit, setLimit] = useState(100);

    // Función para construir el objeto queryParameters basado en el estado actual
    const buildQueryParameters = () => {
        const queryParameters = {
            filters: filters,
            selectedFields: selectedFields,
            sortField: sortField,
            sortDirection: sortDirection,
            selectedOperators: selectedOperators,
            groupedFields: groupedFields,
            limit: limit,
        };
        return queryParameters;
    };
    const handleQuerySubmit = async () => {
        const queryParameters = buildQueryParameters();
        const username = 'witcher.valwolfor';
        const password = 'abrakadabra777';
        const basicAuth = 'Basic ' + btoa(username + ':' + password);

        const processData = (data) => {
            console.log('Data from API:', data);
            // Por ejemplo, actualización de estado, renderizado de resultados, etc.
        };

        const handleError = (error) => {
            console.error('Error retrieving the data:', error);
        };

        const response = await fetch('http://localhost:8085/bigquery/dynamic', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': basicAuth // Agregar la autenticación básica en el encabezado
            },
            body: JSON.stringify(queryParameters)
        })
        .then(response => response.json())
        .then(data => processData(data))
        .catch(error => handleError(error));

        console.log('Query Parameters:', queryParameters);


    };

    return (
        <div>
            {/* Aquí colocarías los componentes para seleccionar campos, establecer filtros, ordenar, etc. */}
            <FieldSelector setSelectedFields={setSelectedFields} selectedFields={selectedFields}/>
            <FiltersBuilder setFilters={setFilters} filters={filters}/>
            <SortOptions setSortField={setSortField} setSortDirection={setSortDirection}
                         selectedFields={selectedFields}/>
            <LimitSelector setLimit={setLimit} limit={limit}/>
            <button onClick={handleQuerySubmit}>Enviar Query</button>
        </div>
    );
};

export default QueryBuilder;
