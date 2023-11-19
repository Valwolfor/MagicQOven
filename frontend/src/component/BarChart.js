import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto'; // Importa Chart.js

const BarChart = ({ data }) => {
    const chartRef = useRef(null);
    const chartInstance = useRef(null); // Referencia al gráfico actual

    useEffect(() => {
        if (!data || data.length === 0) return;

        // Si hay un gráfico existente, destrúyelo antes de crear uno nuevo
        if (chartInstance.current) {
            chartInstance.current.destroy();
        }

        const ctx = chartRef.current.getContext('2d');
        const labels = data.map(item => item.dma_name);
        const values = data.map(item => item.score);

        chartInstance.current = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Score',
                        data: values,
                        backgroundColor: 'rgba(54, 162, 235, 0.6)', // Color de las barras
                        borderColor: 'rgba(54, 162, 235, 1)',
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                scales: {
                    y: {
                        beginAtZero: true,
                    },
                },
            },
        });
    }, [data]);

    return <canvas ref={chartRef} />;
};

export default BarChart;
