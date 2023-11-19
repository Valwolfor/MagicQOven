import React, {useState, useEffect} from 'react';
import BarChart from './BarChart';
import LineChart from "./LineChart";
import DoughnutChart from "./DoughnutChart";
import DoughnutTermsChart from "./DoughnutTermsChart";


const Dashboard = () => {
    const [chartData, setChartData] = useState([]);
    const [dataFetched, setDataFetched] = useState(false);
    const fetchData = async () => {
        // TODO: elimnar lo de autenticación
        const username = 'witcher.valwolfor';
        const password = 'abrakadabra777';
        const queryParameters = {
            filters: {
                1: {
                    field: "score",
                    operator: ">",
                    isInt: "true",
                    operant: "10"
                },
                2: {
                    field: "score",
                    operator: "<",
                    isInt: "true",
                    operant: "35"
                }
            },
            selectedFields: ["dma_id", "dma_name", "term", "week", "score", "rank", "refresh_date"],
            sortField: "week",
            sortDirection: "ASC",
            selectedOperators: ["AND"],
            groupedFields: ["dma_name"],
            limit: 100
        };
        const requestBody = JSON.stringify(queryParameters);

        const basicAuth = 'Basic ' + btoa(username + ':' + password);

        try {
            const response = await fetch('http://localhost:8085/bigquery/dynamic', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': basicAuth // Agregar la autenticación básica en el encabezado
                },
                body: requestBody
            });

            if (!response.ok) {
                throw new Error('Network response was not ok.');
            }

            const data = await response.json();
            // Aquí puedes trabajar con los datos que has recibido
            console.log(data);
            // Luego, puedes establecer el estado del componente con estos datos o usarlos de alguna otra manera
            // processData(data); // Función para manejar los datos
            setChartData(data);
            setDataFetched(true);
            console.log('Data fetched status:', dataFetched);// Marca que los datos se han recuperado
        } catch (error) {
            console.error('There has been a problem with your fetch operation:', error);
            // Manejar el error, mostrar un mensaje al usuario, etc.
        }
    };
    // Suponiendo que tienes tus datos en una variable llamada data
    useEffect(() => {
        setDataFetched(true);
        fetchData();
        console.log('Data fetched status:', dataFetched);
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            <div>

            </div>
            {chartData.length > 0 && <BarChart data={chartData}/>}
            {chartData.length > 0 && <LineChart data={chartData}/>}
            {chartData.length > 0 && <DoughnutChart data={chartData}/>}
            {chartData.length > 0 && <DoughnutTermsChart data={chartData}/>}
        </div>
    );
};

export default Dashboard




