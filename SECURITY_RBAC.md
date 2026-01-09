# Role-Based Access Control (RBAC) Security Layer

## Overview
The application now has a security middleware that prevents users from accessing dashboards they don't have permission for.

## How It Works

### 🔐 Automatic Protection
The security layer automatically runs on every dashboard page load and:
1. Checks if the user is logged in
2. Verifies their role matches the dashboard they're trying to access
3. Redirects them if they don't have permission

### 🎭 Role-Dashboard Mapping

| Role | Allowed Dashboard | File |
|------|------------------|------|
| ADMIN | Admin Dashboard | `admindashboard.html` |
| MANAGER | Manager Dashboard | `manager-dashboard.html` |
| MEMBER | Member Dashboard | `member-dashboard.html` |

## What Happens When...

### ✅ Correct Access
**User:** ADMIN role  
**Tries to access:** `admindashboard.html`  
**Result:** ✅ Access granted

```
🔐 Checking dashboard access...
📄 Current page: admindashboard.html
👤 User Role: ADMIN
🎭 Required role for this dashboard: ADMIN
✅ Access granted for ADMIN to admindashboard.html
```

### ❌ Wrong Dashboard
**User:** MANAGER role  
**Tries to access:** `admindashboard.html`  
**Result:** ❌ Blocked and redirected to `manager-dashboard.html`

```
🔐 Checking dashboard access...
📄 Current page: admindashboard.html
👤 User Role: MANAGER
🎭 Required role for this dashboard: ADMIN
❌ Access denied! User role "MANAGER" cannot access "ADMIN" dashboard
🔄 Redirecting MANAGER to their dashboard: manager-dashboard.html
```

**User sees alert:**
```
Access Denied!

You are logged in as MANAGER.
Redirecting to your dashboard...
```

### ⚠️ Not Logged In
**User:** No authentication  
**Tries to access:** Any dashboard  
**Result:** ⚠️ Redirected to `login.html`

```
🔐 Checking dashboard access...
📄 Current page: manager-dashboard.html
⚠️ User not authenticated, redirecting to login
🔄 Redirecting to login page...
```

## Implementation Details

### Files Modified

1. **`js/common.js`** - Security middleware
   - `DashboardAccess` object with all security logic
   - `initDashboardSecurity()` - Auto-runs on page load
   - `checkAccess()` - Validates user permissions
   - `redirectToLogin()` - Handles unauthenticated users
   - `redirectToCorrectDashboard()` - Redirects to proper dashboard

2. **Dashboard Files** - Security script included
   - `admindashboard.html` ✅
   - `manager-dashboard.html` ✅
   - `member-dashboard.html` ✅

### Security Checks

```javascript
// What gets checked:
1. userToken - Must exist in localStorage
2. userRole - Must match dashboard requirement
3. userId - Must be present
```

### LocalStorage Data Used

```javascript
localStorage.getItem('userToken')  // Authentication token
localStorage.getItem('userRole')   // 'ADMIN', 'MANAGER', or 'MEMBER'
localStorage.getItem('userId')     // User's UUID
```

## Testing the Security

### Test Case 1: Login as ADMIN, try to access Manager Dashboard
1. Login as ADMIN
2. Manually navigate to `manager-dashboard.html`
3. **Expected:** Alert shown, redirected to `admindashboard.html`

### Test Case 2: Login as MANAGER, try to access Admin Dashboard
1. Login as MANAGER
2. Manually navigate to `admindashboard.html`
3. **Expected:** Alert shown, redirected to `manager-dashboard.html`

### Test Case 3: Not logged in, try to access any dashboard
1. Clear localStorage (logout)
2. Navigate to any dashboard
3. **Expected:** Redirected to `login.html`

### Test Case 4: Correct access
1. Login as ADMIN
2. Navigate to `admindashboard.html`
3. **Expected:** ✅ Dashboard loads normally

## Console Debugging

Open browser console (F12) to see detailed security logs:

```javascript
🔐 Checking dashboard access...
📄 Current page: admindashboard.html
👤 User Role: ADMIN
🔑 Has Token: true
🆔 User ID: 550e8400-e29b-41d4-a716-446655440000
🎭 Required role for this dashboard: ADMIN
✅ Access granted for ADMIN to admindashboard.html
```

## Adding New Dashboards

To add a new dashboard to the security layer:

1. **Add to `js/common.js`:**

```javascript
dashboards: {
    'admindashboard.html': 'ADMIN',
    'manager-dashboard.html': 'MANAGER',
    'member-dashboard.html': 'MEMBER',
    'new-dashboard.html': 'NEW_ROLE'  // Add this
},

roleDashboards: {
    'ADMIN': 'admindashboard.html',
    'MANAGER': 'manager-dashboard.html',
    'MEMBER': 'member-dashboard.html',
    'NEW_ROLE': 'new-dashboard.html'  // Add this
}
```

2. **Include security script in new dashboard:**

```html
<script src="./js/common.js"></script>
```

## Logout Functionality

To add a logout button to any dashboard:

```html
<button onclick="DashboardAccess.logout()">Logout</button>
```

This will:
- Clear all localStorage data
- Redirect to login page

## Security Features

✅ **Role-based access control**  
✅ **Automatic redirect to correct dashboard**  
✅ **Fallback to login if not authenticated**  
✅ **Console logging for debugging**  
✅ **User-friendly alerts**  
✅ **Prevents URL manipulation**  
✅ **Clears corrupted auth data**  

## Important Notes

⚠️ **This is client-side security only!**  
- Backend must also validate roles on every API request
- Never trust client-side data alone
- This prevents accidental access and improves UX
- Backend should check JWT token and role for all protected endpoints

⚠️ **Clear browser cache after deploying**  
- Users should hard refresh (Ctrl + F5)
- Or clear localStorage manually

## Troubleshooting

### Issue: Infinite redirect loop
**Cause:** Role in localStorage doesn't match any dashboard  
**Solution:** Clear localStorage and login again

### Issue: Security not working
**Cause:** `common.js` not loaded  
**Solution:** Verify `<script src="./js/common.js"></script>` is in HTML

### Issue: User can still access wrong dashboard
**Cause:** Browser cached old version  
**Solution:** Hard refresh (Ctrl + F5) or clear cache
