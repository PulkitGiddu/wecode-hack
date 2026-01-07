
window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
    
    // Load amenities when page loads
    loadAmenities();
});

// Load and display amenities
async function loadAmenities() {
    try {
        const amenities = await getAllAmenities();
        displayAmenities(amenities);
    } catch (error) {
        console.error('Error loading amenities:', error);
        displayError();
    }
}

// Display amenities in table
function displayAmenities(amenities) {
    const tbody = document.getElementById('amenitiesTableBody');
    
    if (!amenities || amenities.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4" class="text-center">No amenities found</td></tr>';
        return;
    }
    
    tbody.innerHTML = amenities.map((amenity, index) => {
        // Handle both backend format (amenityName, creditCost) and old format
        const amenityName = amenity.amenityName || amenity.name || 'Unknown';
        const formattedName = formatAmenityName(amenityName);
        const creditCost = amenity.creditCost || amenity.creditsScore || 0;
        const isActive = amenity.is_active !== undefined ? amenity.is_active : true;
        
        return `
            <tr>
                <th scope="row">${index + 1}</th>
                <td>${formattedName}</td>
                <td>${creditCost}</td>
                <td>
                    <span class="badge ${isActive ? 'bg-success' : 'bg-secondary'}">
                        ${isActive ? 'Active' : 'Inactive'}
                    </span>
                </td>
            </tr>
        `;
    }).join('');
}

// Display error message
function displayError() {
    const tbody = document.getElementById('amenitiesTableBody');
    tbody.innerHTML = '<tr><td colspan="4" class="text-center text-danger">Error loading amenities. Please try again later.</td></tr>';
}

// Attach functions to window for React
window.loadAmenities = loadAmenities;
window.displayAmenities = displayAmenities;
window.displayError = displayError;
