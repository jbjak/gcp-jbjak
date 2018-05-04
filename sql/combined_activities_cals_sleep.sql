SELECT 
    a.*,
    s.Min_Sleep_Hours_Per_Day as sleep_min,
    s.Max_Sleep_Hours_Per_Day as sleep_max,
    a.Calories_Consumed - a.Exercise_Calories_Burned as net_calories,
    c.Sedentary as cals_sedentary,
	c.Moderately_Active as cals_moderate,
	c.Active as cals_active
FROM
    gym_data.activities a, cdc_guidelines.sleep s, cdc_guidelines.calories c
WHERE
	a.Age <= s.Max_Age AND a.Age >= s.Min_Age
    AND
    a.Age <= c.Max_Age AND a.Age >= c.Min_Age
    AND
    a.Gender = c.Gender
order by
	a.Member_ID, a.Date