SELECT
  t.id
, t.title
, t.description
, t.created_at
, tr.user_id AS reporter
, th.id AS history_id
, th.state AS todo_state
, th.operator
, th.created_at AS updated_at
FROM todo AS t
  JOIN todo_reporters AS tr ON t.id = tr.todo_id
  JOIN todo_history AS th ON t.id = th.todo_id
WHERE
  t.id = /* todoId */1
;
