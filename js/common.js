function openNav() {
    document.getElementById("mySidebar").style.width = "250px";
    document.getElementById("main").style.marginLeft = "250px";
}

function closeNav() {
    document.getElementById("mySidebar").style.width = "0";
    document.getElementById("main").style.marginLeft= "0";
}

window.addEventListener('load', function() {
    setTimeout(function() {
        document.getElementById('myloader').style.display = 'none';
    }, 3000); // 3 seconds
});

// ============================================
// ROLE-BASED ACCESS CONTROL (RBAC)
// ============================================

/**
 * Role-based dashboard access control
 * Prevents users from accessing dashboards they don't have permission for
 */
const DashboardAccess = {
    // Dashboard to role mapping
    dashboards: {
        'admindashboard.html': 'ADMIN',
        'manager-dashboard.html': 'MANAGER',
        'member-dashboard.html': 'MEMBER'
    },
    
    // Role to dashboard mapping (for redirects)
    roleDashboards: {
        'ADMIN': 'admindashboard.html',
        'MANAGER': 'manager-dashboard.html',
        'MEMBER': 'member-dashboard.html'
    },
    
    /**
     * Check if user has access to current page
     * Redirects to appropriate dashboard or login if unauthorized
     */
    checkAccess: function() {
        console.log('🔐 Checking dashboard access...');
        
        // Get current page name
        const currentPage = window.location.pathname.split('/').pop();
        console.log('📄 Current page:', currentPage);
        
        // Skip check for non-dashboard pages
        if (!this.dashboards[currentPage]) {
            console.log('✅ Not a protected dashboard page, skipping check');
            return true;
        }
        
        // Check if user is logged in
        const userToken = localStorage.getItem('userToken');
        const userRole = localStorage.getItem('userRole');
        const userId = localStorage.getItem('userId');
        
        console.log('👤 User Role (from localStorage):', userRole);
        console.log('🔑 Has Token:', !!userToken);
        console.log('🆔 User ID:', userId);
        
        // For now, only check role and userId (token check can be added later)
        // Redirect to login if not authenticated
        if (!userRole || !userId) {
            console.warn('⚠️ User not authenticated');
            console.log('  Role exists:', !!userRole);
            console.log('  UserId exists:', !!userId);
            this.redirectToLogin();
            return false;
        }
        
        // Get required role for current dashboard
        const requiredRole = this.dashboards[currentPage];
        console.log('🎭 Required role for this dashboard:', requiredRole);
        
        // Normalize both roles to uppercase for case-insensitive comparison
        const normalizedUserRole = userRole.toUpperCase().trim();
        const normalizedRequiredRole = requiredRole.toUpperCase().trim();
        
        console.log('🔍 Comparing roles (normalized):');
        console.log('   User Role:', normalizedUserRole);
        console.log('   Required Role:', normalizedRequiredRole);
        console.log('   Match:', normalizedUserRole === normalizedRequiredRole);
        
        // Check if user has correct role
        if (normalizedUserRole !== normalizedRequiredRole) {
            console.warn(`❌ Access denied! User role "${normalizedUserRole}" cannot access "${normalizedRequiredRole}" dashboard`);
            this.redirectToCorrectDashboard(normalizedUserRole);
            return false;
        }
        
        console.log('✅ Access granted for', normalizedUserRole, 'to', currentPage);
        return true;
    },
    
    /**
     * Redirect to login page
     */
    redirectToLogin: function() {
        console.log('🔄 Redirecting to login page...');
        console.log('⚠️ Reason for redirect:');
        
        const userRole = localStorage.getItem('userRole');
        const userId = localStorage.getItem('userId');
        
        if (!userRole) console.log('   ❌ Missing userRole');
        if (!userId) console.log('   ❌ Missing userId');
        
        console.log('🧹 Clearing localStorage...');
        
        // Clear potentially corrupted data
        localStorage.removeItem('userToken');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userId');
        
        console.log('⏳ Redirecting in 3 seconds (time to read console)...');
        
        setTimeout(() => {
            window.location.href = 'login.html';
        }, 3000);
    },
    
    /**
     * Redirect to user's correct dashboard based on role
     */
    redirectToCorrectDashboard: function(role) {
        // Normalize role to uppercase
        const normalizedRole = role.toUpperCase().trim();
        const correctDashboard = this.roleDashboards[normalizedRole];
        
        console.log('🔄 Attempting redirect for role:', normalizedRole);
        console.log('   Correct dashboard:', correctDashboard);
        
        if (correctDashboard) {
            console.log(`🔄 Redirecting ${normalizedRole} to their dashboard: ${correctDashboard}`);
            
            // Show alert to user
            alert(`Access Denied!\n\nYou are logged in as ${normalizedRole}.\nRedirecting to your dashboard...`);
            
            setTimeout(() => {
                window.location.href = correctDashboard;
            }, 100);
        } else {
            console.error('❌ Unknown role:', normalizedRole);
            console.log('   Available roles:', Object.keys(this.roleDashboards));
            this.redirectToLogin();
        }
    },
    
    /**
     * Logout user and redirect to login
     */
    logout: function() {
        console.log('👋 Logging out user...');
        localStorage.removeItem('userToken');
        localStorage.removeItem('userRole');
        localStorage.removeItem('userId');
        localStorage.removeItem('userName');
        localStorage.removeItem('userEmail');
        
        window.location.href = 'login.html';
    }
};

/**
 * Initialize access control on page load
 * Call this function at the top of each dashboard page
 */
function initDashboardSecurity() {
    console.log('🛡️ Initializing dashboard security...');
    
    // Get current page
    const currentPage = window.location.pathname.split('/').pop();
    
    // Skip security check for non-dashboard pages
    const protectedPages = ['admindashboard.html', 'manager-dashboard.html', 'member-dashboard.html'];
    if (!protectedPages.includes(currentPage)) {
        console.log('⏭️ Skipping security check - not a dashboard page');
        return;
    }
    
    console.log('📋 LocalStorage Debug Info:');
    console.log('   userToken:', localStorage.getItem('userToken') ? '✓ exists' : '✗ missing');
    console.log('   userRole:', localStorage.getItem('userRole'));
    console.log('   userId:', localStorage.getItem('userId'));
    console.log('   userName:', localStorage.getItem('userName'));
    
    // Small delay to ensure localStorage is ready
    setTimeout(() => {
        DashboardAccess.checkAccess();
    }, 100);
}

// Only auto-run on dashboard pages
const currentPage = window.location.pathname.split('/').pop();
const isDashboardPage = ['admindashboard.html', 'manager-dashboard.html', 'member-dashboard.html'].includes(currentPage);

if (isDashboardPage) {
    console.log('🎯 Dashboard page detected, enabling security...');
    
    // Auto-run security check when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', function() {
            initDashboardSecurity();
        });
    } else {
        // DOM already loaded
        initDashboardSecurity();
    }
} else {
    console.log('📄 Non-dashboard page, security check disabled');
}