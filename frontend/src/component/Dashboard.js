import React, {useState, useEffect} from 'react';
import BarChart from './charts/BarChart';
import LineChart from "./charts/LineChart";
import DoughnutChart from "./charts/DoughnutChart";
import DoughnutTermsChart from "./charts/DoughnutTermsChart";
import QueryBuilder from "./querybuilder/QueryBuilder";


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

            // Luego, puedes establecer el estado del componente con estos datos o usarlos de alguna otra manera
            // processData(data); // Función para manejar los datos
            setChartData(data);
            setDataFetched(true);

        } catch (error) {
            console.error('There has been a problem with your fetch operation:', error);
            // Manejar el error, mostrar un mensaje al usuario, etc.
        }
    };
    // Suponiendo que tienes tus datos en una variable llamada data
    useEffect(() => {
        setDataFetched(true);
        fetchData();
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            <div>
                <QueryBuilder  />
            </div>
            <div style={{ display: 'flex', flexDirection: 'column' }}>
                {chartData.length > 0 && (
                    <div
                        style={{
                            display: 'flex',
                            flexWrap: 'wrap',
                            justifyContent: 'space-between',
                        }}
                    >

                        <div style={{ width: '48%' }}>
                            <BarChart data={chartData} />
                        </div>
                        <div style={{ width: '48%' }}>
                            <LineChart data={chartData} />
                        </div>
                    </div>
                )}
                {chartData.length > 0 && (
                    <div
                        style={{
                            display: 'flex',
                            flexWrap: 'wrap',
                            justifyContent: 'space-between',
                        }}
                    >

                        <div style={{ width: '48%' }}>
                            <DoughnutChart data={chartData} />
                        </div>
                        <div style={{ width: '48%' }}>
                            <DoughnutTermsChart data={chartData} />
                        </div>
                    </div>
                )}
                {/* Insert here the next charts*/}
            </div>
        </div>
    );
};

export default Dashboard




