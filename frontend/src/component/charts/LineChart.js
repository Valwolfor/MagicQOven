import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const LineChart = ({ data }) => {
    const chartRef = useRef(null);
    const chartInstance = useRef(null);

    useEffect(() => {
        if (!data || data.length === 0) return;

        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const ctx = chartRef.current.getContext('2d');
        const datasets = [];

        // Agrupar datos por término (por ejemplo, 'term')
        const groupedData = data.reduce((acc, item) => {
            if (!acc[item.term]) {
                acc[item.term] = [];
            }
            acc[item.term].push(item);
            return acc;
        }, {});


        // Generar dinámicamente conjuntos de datos para cada término
        Object.keys(groupedData).forEach(term => {
            const termData = groupedData[term];

            const labels = termData.map(item => item.week);
            const values = termData.map(item => item.score);

            const dataset = {
                label: `${term} Score`,
                data: values,
                borderColor: getRandomColor(), // Función para obtener colores aleatorios
                fill: false,
            };

            datasets.push({ labels, dataset });
        });

        chartInstance.current = new Chart(ctx, {
            type: 'line',
            data: {
                labels: datasets[0].labels, // Utilizar las etiquetas del primer conjunto de datos
                datasets: datasets.map(data => data.dataset),
            },
            options: {
                plugins: {
                    legend: {
                        display: true,
                        position: 'top',
                    },
                },
                scales: {
                    x: {
                        title: {
                            display: true,
                            text: 'Week',
                        },
                    },
                    y: {
                        title: {
                            display: true,
                            text: 'Score',
                        },
                        beginAtZero: true,
                    },
                },
            },
        });
    }, [data]);

    // Función para obtener colores aleatorios
    const getRandomColor = () => {
        return `rgba(${Math.floor(Math.random() * 256)}, ${Math.floor(
            Math.random() * 256
        )}, ${Math.floor(Math.random() * 256)}, 1)`;
    };

    return <canvas ref={chartRef} />;
};

export default LineChart;
