import React, { useState } from 'react';
import '../../css/LimitSelector.css'; // Archivo de estilos CSS

const LimitSelector = ({ setLimit, limit }) => {
    // Límite mínimo y máximo permitido
    const minLimit = 1;
    const maxLimit = 1000;

    // Estado para manejar el mensaje de error
    const [error, setError] = useState('');

    // Manejar cambios en el límite
    const handleLimitChange = (e) => {
        let newLimit = parseInt(e.target.value, 10);

        // Verificar si el nuevo límite está dentro del rango permitido
        if (newLimit < minLimit) {
            newLimit = minLimit;
            setError(`El límite mínimo es ${minLimit}`);
        } else if (newLimit > maxLimit) {
            newLimit = maxLimit;
            setError(`El límite máximo es ${maxLimit}`);
        } else {
            setError('');
        }

        // Actualizar el límite
        setLimit(newLimit);
    };

    return (
        <div className="limit-selector">
            <h3>Select Limit</h3>
            <input
                type="number"
                value={limit}
                onChange={handleLimitChange}
                min={minLimit}
                max={maxLimit}
                className={error ? 'limit-error' : ''}
            />
            {error && <p className="error-message">{error}</p>}
        </div>
    );
};

export default LimitSelector;
