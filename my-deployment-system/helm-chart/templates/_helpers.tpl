{{- define "my-deployment-system.fullname" -}}
{{ printf "%s-%s" .Release.Name .Chart.Name }}
{{- end -}}