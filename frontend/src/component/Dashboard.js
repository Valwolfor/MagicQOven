import React, {useState, useEffect} from 'react';
import BarChart from './charts/BarChart';
import LineChart from "./charts/LineChart";
import DoughnutChart from "./charts/DoughnutChart";
import DoughnutTermsChart from "./charts/DoughnutTermsChart";


const Dashboard = ({chartData}) => {
    const [dataFetched, setDataFetched] = useState(false);
    const fetchData = () => {
        setDataFetched(true);
    };

    useEffect(() => {
        fetchData();
    }, []);

    return (
        <div>
            <h1>Dashboard</h1>
            <div>
                <div style={{width: '90%', display: 'flex', justifyContent: 'space-between'}}>
                    <BarChart data={chartData}/>
                </div>
                <div style={{width: '90%', display: 'flex', justifyContent: 'space-between', padding : 50}}>
                    <LineChart data={chartData}/>
                </div>
            </div>
            <div style={{display: 'flex', flexDirection: 'column'}}>

                {chartData.length > 0 && (
                    <div
                        style={{
                            display: 'flex',
                            flexWrap: 'wrap',
                            justifyContent: 'space-between',
                        }}
                    >

                        <div style={{width: '48%'}}>
                            <DoughnutChart data={chartData}/>
                        </div>
                        <div style={{width: '48%'}}>
                            <DoughnutTermsChart data={chartData}/>
                        </div>
                    </div>
                )}
                {/* Insert here the next charts*/}
            </div>
        </div>
    );
};

export default Dashboard




