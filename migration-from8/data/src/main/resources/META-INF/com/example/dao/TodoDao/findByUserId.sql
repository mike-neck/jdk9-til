SELECT
  *
FROM
  todo AS t
  JOIN todo_reporters AS tr ON t.id = tr.todo_id
WHERE
  tr.user_id = /* userId */1
;

