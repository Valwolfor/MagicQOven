import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const DoughnutChart = ({ data }) => {
    const chartRef = useRef(null);
    let chartInstance = null;

    useEffect(() => {
        if (!chartRef || !chartRef.current || !data || data.length === 0) return;

        const ctx = chartRef.current.getContext('2d');

        // Limitar los datos a los primeros 20 elementos
        const slicedData = data.slice(0, 20);

        const labels = slicedData.map(item => item.dma_name);
        const values = slicedData.map(item => item.score);

        if (chartInstance) {
            chartInstance.destroy();
        }

        chartInstance = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: labels,
                        data: values,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.6)',
                            'rgba(54, 162, 235, 0.6)',
                            'rgba(255, 206, 86, 0.6)',
                            'rgba(75, 192, 192, 0.6)',
                            'rgba(153, 102, 255, 0.6)',
                            // Agregar más colores si es necesario
                        ],
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                plugins: {
                    legend: false,
                    title: {
                        display: true,
                        text: 'Doughnut Chart of Top Terms',
                    },
                },
            },
        });

        return () => {
            if (chartInstance) {
                chartInstance.destroy();
                chartInstance = null;
            }
        };
    }, [data]);

    return <canvas ref={chartRef} />;
};

export default DoughnutChart;
