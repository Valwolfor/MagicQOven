import React, { useEffect, useRef } from 'react';
import Chart from 'chart.js/auto';

const DoughnutTermsChart = ({ data }) => {
    const chartRef = useRef(null);
    let chartInstance = null;

    useEffect(() => {
        if (!chartRef || !chartRef.current || !data || data.length === 0) return;

        const ctx = chartRef.current.getContext('2d');

        const termCounts = {};
        data.forEach(item => {
            if (!termCounts[item.term]) {
                termCounts[item.term] = 1;
            } else {
                termCounts[item.term]++;
            }
        });

        const terms = Object.keys(termCounts);
        const counts = Object.values(termCounts);

        if (chartInstance) {
            chartInstance.destroy();
        }

        chartInstance = new Chart(ctx, {
            type: 'doughnut',
            data: {
                labels: terms,
                datasets: [
                    {
                        label: 'Term Count',
                        data: counts,
                        backgroundColor: [
                            'rgba(255, 99, 132, 0.6)',
                            'rgba(54, 162, 235, 0.6)',
                            'rgba(255, 206, 86, 0.6)',
                            'rgba(75, 192, 192, 0.6)',
                            'rgba(153, 102, 255, 0.6)',
                        ],
                        borderWidth: 1,
                    },
                ],
            },
            options: {
                plugins: {
                    title: {
                        display: true,
                        text: 'Doughnut Chart of Term Counts',
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

export default DoughnutTermsChart;
